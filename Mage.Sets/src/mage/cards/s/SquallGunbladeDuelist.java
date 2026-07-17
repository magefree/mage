package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.AttacksPlayerWithCreaturesTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SquallGunbladeDuelist extends CardImpl {

    public SquallGunbladeDuelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // As Squall enters, choose a number.
        this.addAbility(new AsEntersBattlefieldAbility(new SquallGunbladeDuelistChooseEffect()));

        // Whenever one or more creatures attack one of your opponents, if any of those creatures have power or toughness equal to the chosen number, Squall deals damage equal to its power to defending player.
        this.addAbility(new AttacksPlayerWithCreaturesTriggeredAbility(
                new SquallGunbladeDuelistDamageEffect(), 1,
                StaticFilters.FILTER_PERMANENT_CREATURE, SetTargetPointer.PLAYER, true
        ).withInterveningIf(SquallGunbladeDuelistCondition.instance)
                .setTriggerPhrase("Whenever one or more creatures attack one of your opponents, "));
    }

    private SquallGunbladeDuelist(final SquallGunbladeDuelist card) {
        super(card);
    }

    @Override
    public SquallGunbladeDuelist copy() {
        return new SquallGunbladeDuelist(this);
    }
}

enum SquallGunbladeDuelistCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Integer number = (Integer) game
                .getState()
                .getValue(CardUtil.getObjectZoneString(
                        "chosenNumber", source.getSourceId(), game,
                        game.getState().getZoneChangeCounter(source.getSourceId()), true
                ));
        return source
                .getAllEffects()
                .stream()
                .map(effect -> (Set<Permanent>) effect.getValue("attackingCreatures"))
                .filter(Objects::nonNull)
                .findFirst()
                .map(Collection::stream)
                .filter(stream -> stream.anyMatch(
                        permanent -> permanent.getPower().getValue() == number
                                || permanent.getToughness().getValue() == number
                ))
                .isPresent();
    }

    @Override
    public String toString() {
        return "any of those creatures have power or toughness equal to the chosen number";
    }
}

class SquallGunbladeDuelistChooseEffect extends OneShotEffect {

    SquallGunbladeDuelistChooseEffect() {
        super(Outcome.Benefit);
        staticText = "choose a number";
    }

    private SquallGunbladeDuelistChooseEffect(final SquallGunbladeDuelistChooseEffect effect) {
        super(effect);
    }

    @Override
    public SquallGunbladeDuelistChooseEffect copy() {
        return new SquallGunbladeDuelistChooseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int number = player.getAmount(0, Integer.MAX_VALUE, "Choose a number", source, game);
        game.getState().setValue(CardUtil.getObjectZoneString(
                "chosenNumber", source.getSourceId(), game,
                game.getState().getZoneChangeCounter(source.getSourceId()), false
        ), number);
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent != null) {
            permanent.addInfo("chosen number", "<font color = 'blue'>Chosen Number: " + number + "</font>", game);
            game.informPlayers(permanent.getLogName() + ", chosen number: " + number);
        }
        return true;
    }
}

class SquallGunbladeDuelistDamageEffect extends OneShotEffect {

    SquallGunbladeDuelistDamageEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals damage equal to its power to defending player";
    }

    private SquallGunbladeDuelistDamageEffect(final SquallGunbladeDuelistDamageEffect effect) {
        super(effect);
    }

    @Override
    public SquallGunbladeDuelistDamageEffect copy() {
        return new SquallGunbladeDuelistDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        return player != null
                && permanent != null
                && player.damage(permanent.getPower().getValue(), permanent.getId(), source, game) > 0;
    }
}
