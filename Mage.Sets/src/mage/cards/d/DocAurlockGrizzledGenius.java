package mage.cards.d;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DocAurlockGrizzledGenius extends CardImpl {

    private static final FilterCard filter = new FilterCard("Spells you cast from your graveyard or from exile");

    static {
        filter.add(Predicates.or(
                new CastFromZonePredicate(Zone.GRAVEYARD),
                new CastFromZonePredicate(Zone.EXILED)
        ));
    }

    public DocAurlockGrizzledGenius(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEAR);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Spells you cast from your graveyard or from exile cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 2)));

        // Plotting cards from your hand costs {2} less.
        this.addAbility(new SimpleStaticAbility(new DocAurlockGrizzledGeniusEffect()));
    }

    private DocAurlockGrizzledGenius(final DocAurlockGrizzledGenius card) {
        super(card);
    }

    @Override
    public DocAurlockGrizzledGenius copy() {
        return new DocAurlockGrizzledGenius(this);
    }
}

/**
 * Inspired by {@link mage.cards.f.Fluctuator}
 */
class DocAurlockGrizzledGeniusEffect extends CostModificationEffectImpl {

    public DocAurlockGrizzledGeniusEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Plotting cards from your hand costs {2} less";
    }

    private DocAurlockGrizzledGeniusEffect(final DocAurlockGrizzledGeniusEffect effect) {
        super(effect);
    }

    @Override
    public DocAurlockGrizzledGeniusEffect copy() {
        return new DocAurlockGrizzledGeniusEffect(this);
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.isControlledBy(source.getControllerId())
                && (abilityToModify instanceof PlotAbility);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        if (!Zone.HAND.equals(game.getState().getZone(abilityToModify.getSourceId()))) {
            return false;
        }
        Player controller = game.getPlayer(abilityToModify.getControllerId());
        if (controller != null) {
            Mana mana = abilityToModify.getManaCostsToPay().getMana();
            int reduce = mana.getGeneric();
            if (reduce > 2) {
                reduce = 2;
            }
            if (reduce > 0) {
                CardUtil.reduceCost(abilityToModify, reduce);
            }
            return true;
        }
        return false;
    }
}
