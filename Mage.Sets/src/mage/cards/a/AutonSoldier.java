package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AutonSoldier extends CardImpl {

    private static final CopyApplier applier = new CopyApplier() {
        @Override
        public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
            blueprint.removeSuperType(SuperType.LEGENDARY);
            blueprint.addCardType(CardType.ARTIFACT);
            blueprint.getAbilities().add(new MyriadAbility());
            return true;
        }
    };

    public AutonSoldier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Auton Soldier enter the battlefield as a copy of any creature on the battlefield, except it isn't legendary, is an artifact in addition to its other types, and has myriad.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new EntersBattlefieldEffect(new CopyPermanentEffect(
                        StaticFilters.FILTER_PERMANENT_CREATURE, applier
                ).setText("You may have {this} enter the battlefield as a copy of any creature on the battlefield, " +
                        "except it isn't legendary, is an artifact in addition to its other types, and has myriad"), "", true))
        );
    }

    private AutonSoldier(final AutonSoldier card) {
        super(card);
    }

    @Override
    public AutonSoldier copy() {
        return new AutonSoldier(this);
    }
}
