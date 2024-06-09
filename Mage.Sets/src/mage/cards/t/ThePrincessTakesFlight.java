package mage.cards.t;

import java.util.UUID;

import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;

/**
 *
 * @author Xanderhall
 */
public final class ThePrincessTakesFlight extends CardImpl {

    public ThePrincessTakesFlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        
        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Exile up to one target creature.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new Effects(new ExileTargetForSourceEffect()), new TargetCreaturePermanent(0, 1));

        // II -- Target creature you control gets +2/+2 and gains flying until end of turn.
        Effects effects = new Effects(
            new BoostTargetEffect(2, 2).setText("target creature you control gets +2/+2"),
            new GainAbilityTargetEffect(FlyingAbility.getInstance()).setText("and gains flying until end of turn")
            );
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, effects, new TargetControlledCreaturePermanent());

        // III -- Return the exiled card to the battlefield under its owner's control.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD));

        this.addAbility(sagaAbility);
    }

    private ThePrincessTakesFlight(final ThePrincessTakesFlight card) {
        super(card);
    }

    @Override
    public ThePrincessTakesFlight copy() {
        return new ThePrincessTakesFlight(this);
    }
}
