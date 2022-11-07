package mage.cards.m;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MachineGodsEffigy extends CardImpl {

    public MachineGodsEffigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // You may have Machine God's Effigy enter the battlefield as a copy of any creature on the battlefield, except it's an artifact and it has "{T}: Add {U}."
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new EntersBattlefieldEffect(
                        new CopyPermanentEffect(StaticFilters.FILTER_PERMANENT_CREATURE, new MachineGodsEffigyApplier())
                                .setText("You may have {this} enter the battlefield as a copy of any creature " +
                                        "on the battlefield, except it's an artifact and it has \"{T}: Add {U}.\""),
                        "", true
                )
        ));

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());
    }

    private MachineGodsEffigy(final MachineGodsEffigy card) {
        super(card);
    }

    @Override
    public MachineGodsEffigy copy() {
        return new MachineGodsEffigy(this);
    }
}

class MachineGodsEffigyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
        blueprint.retainAllArtifactSubTypes(null);
        blueprint.removeAllCardTypes(game);
        blueprint.addCardType(CardType.ARTIFACT);
        blueprint.getAbilities().add(new BlueManaAbility());
        return true;
    }
}