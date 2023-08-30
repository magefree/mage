package mage.cards.f;

import mage.MageInt;
import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.keyword.TrampleAbility;
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
public final class FerociousWerefox extends AdventureCard {

    public FerociousWerefox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{3}{G}", "Guard Change", "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Guard Change
        // Create a Monster Role token attached to target creature you control.
        this.getSpellCard().getSpellAbility().addEffect(new CreateRoleAttachedTargetEffect(RoleType.MONSTER));
        this.getSpellCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        this.finalizeAdventure();
    }

    private FerociousWerefox(final FerociousWerefox card) {
        super(card);
    }

    @Override
    public FerociousWerefox copy() {
        return new FerociousWerefox(this);
    }
}
