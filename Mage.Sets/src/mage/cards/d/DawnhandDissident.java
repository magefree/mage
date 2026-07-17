package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.BlightCost;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DawnhandDissident extends CardImpl {

    public DawnhandDissident(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}, Blight 1: Surveil 1.
        Ability ability = new SimpleActivatedAbility(new SurveilEffect(1), new TapSourceCost());
        ability.addCost(new BlightCost(1));
        this.addAbility(ability);

        // {T}, Blight 2: Exile target card from a graveyard.
        ability = new SimpleActivatedAbility(new ExileTargetForSourceEffect(), new TapSourceCost());
        ability.addCost(new BlightCost(2));
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);

        // During your turn, you may cast creature spells from among cards you own exiled with this creature by removing three counters from among creatures you control in addition to paying their other costs.
        this.addAbility(new SimpleStaticAbility(new DawnhandDissidentEffect()));
    }

    private DawnhandDissident(final DawnhandDissident card) {
        super(card);
    }

    @Override
    public DawnhandDissident copy() {
        return new DawnhandDissident(this);
    }
}

class DawnhandDissidentEffect extends AsThoughEffectImpl {

    DawnhandDissidentEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.AIDontUseIt);
        staticText = "during your turn, you may cast creature spells from among cards you own exiled with this creature " +
                "by removing three counters from among creatures you control in addition to paying their other costs";
    }

    private DawnhandDissidentEffect(final DawnhandDissidentEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DawnhandDissidentEffect copy() {
        return new DawnhandDissidentEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId) || !game.isActivePlayer(affectedControllerId)) {
            return false;
        }
        ExileZone exileZone = game
                .getExile()
                .getExileZone(CardUtil.getExileZoneId(
                        game, source.getSourceId(),
                        game.getState().getZoneChangeCounter(source.getSourceId())
                ));
        if (exileZone == null) {
            return false;
        }
        Card card = game.getCard(objectId);
        Player player = game.getPlayer(affectedControllerId);
        if (card == null
                || player == null
                || !card.isOwnedBy(affectedControllerId)
                || !card.isCreature(game)
                || !exileZone.contains(card.getId())) {
            return false;
        }
        Costs<Cost> newCosts = new CostsImpl<>();
        newCosts.addAll(card.getSpellAbility().getCosts());
        newCosts.add(new RemoveCounterCost(
                new TargetControlledCreaturePermanent(), null, 3
        ).setText("remove three counters from among creatures you control"));
        player.setCastSourceIdWithAlternateMana(
                card.getId(), card.getManaCost(), newCosts
        );
        return true;
    }
}
