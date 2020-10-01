package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacesCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.functions.ApplyToPermanent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class GlasspoolMimic extends ModalDoubleFacesCard {

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
                new CopyPermanentEffect(StaticFilters.FILTER_CONTROLLED_CREATURE, new GlasspoolMimicApplier()),
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

class GlasspoolMimicApplier extends ApplyToPermanent {

    @Override
    public boolean apply(Game game, Permanent permanent, Ability source, UUID copyToObjectId) {
        permanent.addSubType(game, SubType.SHAPESHIFTER);
        permanent.addSubType(game, SubType.ROGUE);
        return true;
    }

    @Override
    public boolean apply(Game game, MageObject mageObject, Ability source, UUID copyToObjectId) {
        mageObject.addSubType(game, SubType.SHAPESHIFTER);
        mageObject.addSubType(game, SubType.ROGUE);
        return true;
    }
}
