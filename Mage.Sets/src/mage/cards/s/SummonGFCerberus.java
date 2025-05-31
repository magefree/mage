package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.delayed.CopyNextSpellDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonGFCerberus extends CardImpl {

    public SummonGFCerberus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Surveil 1.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new SurveilEffect(1));

        // II -- Double -- When you next cast an instant or sorcery spell this turn, copy it. You may choose new targets for the copy.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, ability -> {
            ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new CopyNextSpellDelayedTriggeredAbility()));
            ability.withFlavorWord("Double");
        });

        // III -- Triple -- When you next cast an instant or sorcery spell this turn, copy it twice. You may choose new targets for the copies.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, ability -> {
            ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new CopyNextSpellDelayedTriggeredAbility(
                    StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY, new SummonGFCerberusEffect(),
                    "When you next cast an instant or sorcery spell this turn, " +
                            "copy it twice. You may choose new targets for the copies."
            )));
            ability.withFlavorWord("Triple");
        });
        this.addAbility(sagaAbility);
    }

    private SummonGFCerberus(final SummonGFCerberus card) {
        super(card);
    }

    @Override
    public SummonGFCerberus copy() {
        return new SummonGFCerberus(this);
    }
}

class SummonGFCerberusEffect extends OneShotEffect {

    SummonGFCerberusEffect() {
        super(Outcome.Benefit);
    }

    private SummonGFCerberusEffect(final SummonGFCerberusEffect effect) {
        super(effect);
    }

    @Override
    public SummonGFCerberusEffect copy() {
        return new SummonGFCerberusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        if (spell != null) {
            spell.createCopyOnStack(game, source, source.getControllerId(), true, 2);
            return true;
        }
        return false;
    }
}
