package mage.cards.c;

import mage.MageInt;
import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.keyword.MenaceAbility;
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
public final class ConceitedWitch extends AdventureCard {

    public ConceitedWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{2}{B}", "Price of Beauty", "{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Price of Beauty
        // Create a Wicked Role token attached to target creature you control.
        this.getSpellCard().getSpellAbility().addEffect(new CreateRoleAttachedTargetEffect(RoleType.WICKED));
        this.getSpellCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        this.finalizeAdventure();
    }

    private ConceitedWitch(final ConceitedWitch card) {
        super(card);
    }

    @Override
    public ConceitedWitch copy() {
        return new ConceitedWitch(this);
    }
}
