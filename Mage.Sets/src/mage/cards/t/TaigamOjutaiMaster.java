package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.AttackedThisTurnSourceCondition;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CantBeCounteredControlledEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.Optional;
import java.util.UUID;

/**
 * @author spjspj
 */
public final class TaigamOjutaiMaster extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant, sorcery, and Dragon spells you control");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate(),
                SubType.DRAGON.getPredicate()
        ));
    }

    public TaigamOjutaiMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Instant, sorcery, and Dragon spells you control can't be countered.
        this.addAbility(new SimpleStaticAbility(new CantBeCounteredControlledEffect(filter, Duration.WhileOnBattlefield)));

        // Whenever you cast an instant or sorcery spell from your hand, if Taigam, Ojutai Master attacked this turn, that spell gains rebound.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                Zone.BATTLEFIELD, new TaigamOjutaiMasterEffect(),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY,
                false, SetTargetPointer.SPELL, Zone.HAND
        ).withInterveningIf(AttackedThisTurnSourceCondition.instance));
    }

    private TaigamOjutaiMaster(final TaigamOjutaiMaster card) {
        super(card);
    }

    @Override
    public TaigamOjutaiMaster copy() {
        return new TaigamOjutaiMaster(this);
    }
}

class TaigamOjutaiMasterEffect extends ContinuousEffectImpl {

    TaigamOjutaiMasterEffect() {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "that spell gains rebound";
    }

    private TaigamOjutaiMasterEffect(final TaigamOjutaiMasterEffect effect) {
        super(effect);
    }

    @Override
    public TaigamOjutaiMasterEffect copy() {
        return new TaigamOjutaiMasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell == null) {
            discard();
            return false;
        }
        Optional.of(spell)
                .map(Spell::getCard)
                .filter(card -> !card.getAbilities(game).containsClass(ReboundAbility.class))
                .ifPresent(card -> game.getState().addOtherAbility(card, new ReboundAbility()));
        return true;
    }
}
