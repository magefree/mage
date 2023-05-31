package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class GlasspoolMimic extends ModalDoubleFacedCard {

    public GlasspoolMimic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SHAPESHIFTER, SubType.ROGUE}, "{2}{U}",
                "Glasspool Shore", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Glasspool Mimic
        // Creature — Shapeshifter Rogue
        this.getLeftHalfCard().setPT(new MageInt(0), new MageInt(0));

        // You may have Glasspool Mimic enter the battlefield as a copy of a creature you control, except it's a Shapeshifter Rogue in addition to its other types.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldAbility(
                new CopyPermanentEffect(StaticFilters.FILTER_CONTROLLED_CREATURE, new GlasspoolMimicCopyApplier()),
                true, null, "You may have {this} enter the battlefield as a copy of " +
                "a creature you control, except it's a Shapeshifter Rogue in addition to its other types.", ""
        ));

        // 2.
        // Glasspool Shore
        // Land

        // Glasspool Shore enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {U}.
        this.getRightHalfCard().addAbility(new BlueManaAbility());
    }

    private GlasspoolMimic(final GlasspoolMimic card) {
        super(card);
    }

    @Override
    public GlasspoolMimic copy() {
        return new GlasspoolMimic(this);
    }
}

class GlasspoolMimicCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        blueprint.addSubType(SubType.SHAPESHIFTER, SubType.ROGUE);
        return true;
    }
}
