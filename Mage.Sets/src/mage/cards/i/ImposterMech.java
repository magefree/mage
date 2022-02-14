package mage.cards.i;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImposterMech extends CardImpl {

    private static final CopyApplier applier = new CopyApplier() {
        @Override
        public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
            blueprint.removeAllCardTypes(game);
            blueprint.addCardType(game, CardType.ARTIFACT);
            blueprint.addSubType(game, SubType.VEHICLE);
            blueprint.getAbilities().add(new CrewAbility(3));
            return true;
        }
    };

    public ImposterMech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // You may have Imposter Mech enter the battlefield as a copy of a creature an opponent controls, except its a Vehicle artifact with crew 3 and it loses all other card types.
        this.addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(
                StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE, applier
        ).setText("You may have {this} enter the battlefield as a copy of a creature an opponent controls, " +
                "except its a Vehicle artifact with crew 3 and it loses all other card types.")));

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private ImposterMech(final ImposterMech card) {
        super(card);
    }

    @Override
    public ImposterMech copy() {
        return new ImposterMech(this);
    }
}
