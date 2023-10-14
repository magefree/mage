package mage.cards.v;

import mage.MageInt;
import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.RoleType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VantressTransmuter extends AdventureCard {

    public VantressTransmuter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{3}{U}", "Croaking Curse", "{1}{U}");


        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Croaking Curse
        // Tap target creature. Create a Cursed Role token attached to it.
        this.getSpellCard().getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellCard().getSpellAbility().addEffect(new CreateRoleAttachedTargetEffect(RoleType.CURSED)
                .setText("create a Cursed Role token attached to it"));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        this.finalizeAdventure();
    }

    private VantressTransmuter(final VantressTransmuter card) {
        super(card);
    }

    @Override
    public VantressTransmuter copy() {
        return new VantressTransmuter(this);
    }
}
