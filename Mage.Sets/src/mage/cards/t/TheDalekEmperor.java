package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.FaceVillainousChoiceOpponentsEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.AffinityAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.FaceVillainousChoice;
import mage.choices.VillainousChoice;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DalekToken;
import mage.players.Player;
import mage.target.common.TargetSacrifice;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheDalekEmperor extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.DALEK, "Daleks");
    private static final FaceVillainousChoice choice = new FaceVillainousChoice(
            Outcome.Sacrifice, new TheDalekEmperorFirstChoice(), new TheDalekEmperorSecondChoice()
    );

    public TheDalekEmperor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DALEK);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Affinity for Daleks
        this.addAbility(new AffinityAbility(AffinityType.DALEKS));

        // Other Daleks you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        )));

        // At the beginning of combat on your turn, each opponent faces a villainous choice -- That player sacrifices a creature they control, or you create a 3/3 black Dalek artifact creature token with menace.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new FaceVillainousChoiceOpponentsEffect(choice)));
    }

    private TheDalekEmperor(final TheDalekEmperor card) {
        super(card);
    }

    @Override
    public TheDalekEmperor copy() {
        return new TheDalekEmperor(this);
    }
}

class TheDalekEmperorFirstChoice extends VillainousChoice {

    TheDalekEmperorFirstChoice() {
        super("That player sacrifices a creature of their choice", "You sacrifice a creature");
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        if (!game.getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                player.getId(), source, game, 1
        )) {
            return false;
        }
        TargetSacrifice target = new TargetSacrifice(StaticFilters.FILTER_PERMANENT_CREATURE);
        player.choose(Outcome.Sacrifice, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null && permanent.sacrifice(source, game);
    }
}

class TheDalekEmperorSecondChoice extends VillainousChoice {

    TheDalekEmperorSecondChoice() {
        super("you create a 3/3 black Dalek artifact creature token with menace", "{controller} creates a Dalek token");
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        return new DalekToken().putOntoBattlefield(1, game, source);
    }
}
