package mage.cards.o;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.DontUntapInControllersUntapStepTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class OriginOfIronMan extends CardImpl {

    private static final FilterCard filter = new FilterArtifactCard("an artifact card with mana value 5 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 6));
    }

    public OriginOfIronMan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Tap up to one target creature. It doesn't untap during its controller's untap step for as long as this Saga remains on the battlefield.
        Effects effects = new Effects();
        effects.add(new TapTargetEffect());
        ContinuousRuleModifyingEffect skipUntapEffect = new DontUntapInControllersUntapStepTargetEffect(Duration.WhileOnBattlefield);
        skipUntapEffect.setText("It doesn't untap during its controller's untap step for as long as this Saga remains on the battlefield");
        effects.add(skipUntapEffect);
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, effects, new TargetCreaturePermanent(0, 1));

        // II -- Draw two cards.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new DrawCardSourceControllerEffect(2));

        // III -- You may put an artifact card with mana value 5 or less from your hand onto the battlefield.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new PutCardFromHandOntoBattlefieldEffect(filter));

        this.addAbility(sagaAbility);
    }

    private OriginOfIronMan(final OriginOfIronMan card) {
        super(card);
    }

    @Override
    public OriginOfIronMan copy() {
        return new OriginOfIronMan(this);
    }
}
