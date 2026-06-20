package mage.view;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class ValidateViews {

    // All those class are sent to client as Views or inside Views.
    // The test in this file is attempting to find any class that should
    // not be sent.
    private static final List<Class<?>> toValidate = Arrays.asList(
            // Views
            AbilityPickerView.class, AbilityView.class, CardsView.class,
            CardView.class, ChatMessage.class, CombatGroupView.class,
            CommanderView.class, CommandObjectView.class, CounterView.class,
            DeckView.class, DraftClientMessage.class, DraftPickView.class,
            DraftView.class, DungeonView.class, EmblemView.class,
            ExileView.class, GameClientMessage.class, GameEndView.class,
            GameTypeView.class, GameView.class, LookedAtView.class,
            ManaPoolView.class, MatchView.class, PermanentView.class,
            PermanentView.class, PlaneView.class, PlayerView.class,
            RevealedView.class, RoomUsersView.class, RoundView.class,
            SeatView.class, SelectableObjectView.class, SimpleCardsView.class,
            SimpleCardView.class, StackAbilityView.class, TableClientMessage.class,
            TableView.class, TournamentGameView.class, TournamentPlayerView.class,
            TournamentTypeView.class, TournamentView.class, UserDataView.class,
            UserRequestMessage.class, UsersView.class, UserView.class,

            ChatMessage.MessageColor.class,
            ChatMessage.SoundToPlay.class,
            ChatMessage.MessageType.class,

            // From mage
            mage.constants.MageObjectType.class,
            mage.constants.AbilityType.class,
            mage.players.PlayerType.class,

            mage.MageInt.class,
            mage.ObjectColor.class,

            mage.constants.CardType.class,
            mage.constants.CardType.CardTypePredicate.class,
            mage.constants.SuperType.class,
            mage.constants.SuperType.SuperTypePredicate.class,
            mage.constants.SubType.class,
            mage.constants.SubType.SubTypePredicate.class,
            mage.constants.SubTypeSet.class,
            mage.util.SubTypes.class,

            mage.constants.Rarity.class,

            mage.constants.Zone.class,

            mage.constants.PhaseStep.class,
            mage.constants.TurnPhase.class,

            mage.players.PlayableObjectStats.class,
            mage.players.PlayableObjectsList.class,

            mage.counters.Counters.class,
            mage.counters.Counter.class,
            mage.filter.FilterMana.class,

            mage.abilities.icon.CardIcon.class,

            mage.cards.ArtRect.class,
            java.awt.geom.Rectangle2D.class,
            mage.cards.FrameStyle.class,
            mage.cards.FrameStyle.BorderType.class,

            mage.choices.Choice.class,
            mage.util.MultiAmountMessage.class,
            mage.constants.PlayerAction.class,

            mage.constants.SkillLevel.class,
            mage.constants.TableState.class,

            mage.players.net.UserData.class,
            mage.players.net.UserSkipPrioritySteps.class,
            mage.players.net.SkipPrioritySteps.class
    );

    // Those are safe java class we allow in Views.
    private static final List<Class<?>> safeClass = Arrays.asList(
            boolean.class, int.class, long.class, float.class,
            String.class, Date.class, UUID.class
    );

    // Those are non-public class
    private static final List<String> nonPublicNames = Arrays.asList(
            "mage.players.PlayableObjectRecord"
    );

    // Those are excluded as Reflection on type of Map/Set/Collection is really hard.
    private static final List<String> tooHardToRecurseInto = Arrays.asList(
            "mage.view.CardsView<java.util.LinkedHashMap>",        // <UUID, CardView>
            "mage.view.CardsView<java.util.HashMap>",              // <UUID, CardView>
            "mage.view.CardsView<java.util.AbstractMap>",          // <UUID, CardView>
            "mage.view.ExileView<java.util.LinkedHashMap>",        // <UUID, CardView>
            "mage.view.ExileView<java.util.HashMap>",              // <UUID, CardView>
            "mage.view.ExileView<java.util.AbstractMap>",          // <UUID, CardView>
            "mage.view.SimpleCardsView<java.util.LinkedHashMap>",  // <UUID, CardView>
            "mage.view.SimpleCardsView<java.util.HashMap>",        // <UUID, CardView>
            "mage.view.SimpleCardsView<java.util.AbstractMap>",    // <UUID, CardView>

            "mage.counters.Counters<java.util.AbstractMap>",       // <String, Counter>
            "mage.counters.Counters<java.util.HashMap>",           // <String, Counter>

            "mage.util.SubTypes<java.util.ArrayList>"              // <SubType>
    );

    // Same, but for fields.
    private static final List<String> tooHardToRecurseIntoField = Arrays.asList(
            "mage.view.GameClientMessage: <mage.view.GameClientMessage>::targets<java.util.Set>"  // <UUID>
    );

    @Test
    public void validateViewsContainSafeClass() {
        for (Class<?> clazz : toValidate) {
            validateView(clazz);
        }
    }

    private void validateView(Class clazz) {

        Class subClass = clazz;
        while (subClass != Object.class && subClass != null) {
            for (Field field : subClass.getDeclaredFields()) {
                if (field.getName().startsWith("$VALUES")) {
                    // enum values.
                    continue;
                }
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                if (tooHardToRecurseInto.contains(clazz.getName() + "<" + subClass.getName() + ">")) {
                    continue;
                }
                expectedClass(field.getGenericType(), field.getType(), clazz.getName() + ": <" + subClass.getName() + ">::" + field.getName());
            }

            subClass = subClass.getSuperclass();
        }
    }

    private void expectedClass(Type type, Class clazz, String msg) {
        if (toValidate.contains(clazz)) {
            return;
        }
        if (safeClass.contains(clazz)) {
            return;
        }
        if (nonPublicNames.contains(clazz.getName())) {
            return;
        }
        if (clazz.equals(List.class) || clazz.equals(ArrayList.class) || clazz.equals(Map.class)) {
            ParameterizedType paramType = (ParameterizedType) type;
            if (type != null) {
                Class paramClass = (Class) paramType.getActualTypeArguments()[0];
                if (paramClass != null) {
                    expectedClass(null, paramClass, msg + "::List");
                    return;
                }
            }
        }

        msg = msg + "<" + clazz.getName() + ">";
        if (tooHardToRecurseIntoField.contains(msg)) {
            return;
        }

        // How to fix: If you added a new type meant to be sent to the client, add it
        // in one of the class List above.
        Assert.fail("Unexpected type in View " + msg);
    }
}
