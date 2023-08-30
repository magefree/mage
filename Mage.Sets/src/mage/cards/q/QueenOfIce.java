package mage.cards.q;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QueenOfIce extends AdventureCard {

    public QueenOfIce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{2}{U}", "Rage of Winter", "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Queen of Ice deals combat damage to a creature, tap that creature. It doesn't untap during its controller's next untap step.
        Ability ability = new DealsDamageToACreatureTriggeredAbility(
                new TapTargetEffect("tap that creature"),
                true, false, true
        );
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect()
                .setText("It doesn't untap during its controller's next untap step")
        );
        this.addAbility(ability);

        // Rage of Winter
        // Tap target creature. It doesn’t untap during its controller’s next untap step.
        this.getSpellCard().getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellCard().getSpellAbility().addEffect(new DontUntapInControllersNextUntapStepTargetEffect()
                .setText("It doesn't untap during its controller's next untap step"));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        this.finalizeAdventure();
    }

    private QueenOfIce(final QueenOfIce card) {
        super(card);
    }

    @Override
    public QueenOfIce copy() {
        return new QueenOfIce(this);
    }
}
// let it go
