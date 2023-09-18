package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.CardUtil;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PeerlessSamurai extends CardImpl {

    public PeerlessSamurai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever a Samurai or Warrior you control attacks alone, the next spell you cast this turn costs {1} less to cast.
        this.addAbility(new AttacksAloneControlledTriggeredAbility(
                new PeerlessSamuraiEffect(),
                StaticFilters.FILTER_CONTROLLED_SAMURAI_OR_WARRIOR,
                false, false
        ));
    }

    private PeerlessSamurai(final PeerlessSamurai card) {
        super(card);
    }

    @Override
    public PeerlessSamurai copy() {
        return new PeerlessSamurai(this);
    }
}

class PeerlessSamuraiEffect extends CostModificationEffectImpl {

    int spellsCast;

    public PeerlessSamuraiEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "the next spell you cast this turn costs {1} less to cast";
    }

    protected PeerlessSamuraiEffect(final PeerlessSamuraiEffect effect) {
        super(effect);
        this.spellsCast = effect.spellsCast;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if (watcher != null) {
            spellsCast = watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(source.getControllerId());
        }
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if (watcher != null) {
            if (watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(source.getControllerId()) > spellsCast) {
                discard(); // only one use 
                return false;
            }
        }
        if (abilityToModify instanceof SpellAbility) {
            return abilityToModify.isControlledBy(source.getControllerId());
        }
        return false;
    }

    @Override
    public PeerlessSamuraiEffect copy() {
        return new PeerlessSamuraiEffect(this);
    }
}
