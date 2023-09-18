package mage.cards.b;

import mage.MageInt;
import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.RoleType;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BesottedKnight extends AdventureCard {

    public BesottedKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{3}{W}", "Betroth the Beast", "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Betroth the Beast
        // Create a Royal Role token attached to target creature you control.
        this.getSpellCard().getSpellAbility().addEffect(new CreateRoleAttachedTargetEffect(RoleType.ROYAL));
        this.getSpellCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        this.finalizeAdventure();
    }

    private BesottedKnight(final BesottedKnight card) {
        super(card);
    }

    @Override
    public BesottedKnight copy() {
        return new BesottedKnight(this);
    }
}
