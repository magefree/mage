package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseGameTargetPlayerEffect;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonPrimalOdin extends CardImpl {

    public SummonPrimalOdin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Gungnir -- Destroy target creature an opponent controls.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, ability -> {
            ability.addEffect(new DestroyTargetEffect());
            ability.addTarget(new TargetOpponentsCreaturePermanent());
            ability.withFlavorWord("Gungnir");
        });

        // II -- Zantetsuken -- This creature gains "Whenever this creature deals combat damage to a player, that player loses the game."
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, ability -> {
            ability.addEffect(new GainAbilitySourceEffect(
                    new DealsCombatDamageToAPlayerTriggeredAbility(
                            new LoseGameTargetPlayerEffect(), false, true
                    ), Duration.Custom
            ).setText("{this} gains \"Whenever this creature deals combat damage to a player, that player loses the game.\""));
            ability.withFlavorWord("Zantetsuken");
        });

        // III -- Hall of Sorrow -- Draw two cards. Each player loses 2 life.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, ability -> {
            ability.addEffect(new DrawCardSourceControllerEffect(2));
            ability.addEffect(new LoseLifeAllPlayersEffect(2));
            ability.withFlavorWord("Hall of Sorrow");
        });
        this.addAbility(sagaAbility);
    }

    private SummonPrimalOdin(final SummonPrimalOdin card) {
        super(card);
    }

    @Override
    public SummonPrimalOdin copy() {
        return new SummonPrimalOdin(this);
    }
}
