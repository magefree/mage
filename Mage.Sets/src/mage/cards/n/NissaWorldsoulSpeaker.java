package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceIsSpellCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class NissaWorldsoulSpeaker extends CardImpl {

    public NissaWorldsoulSpeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF, SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Landfall -- Whenever a land you control enters, you get {E}{E}.
        this.addAbility(new LandfallAbility(
                new GetEnergyCountersControllerEffect(2), false
        ));

        // You may pay eight {E} rather than pay the mana cost for permanent spells you cast.
        this.addAbility(new SimpleStaticAbility(new NissaWorldsoulSpeakerEffect()));
    }

    private NissaWorldsoulSpeaker(final NissaWorldsoulSpeaker card) {
        super(card);
    }

    @Override
    public NissaWorldsoulSpeaker copy() {
        return new NissaWorldsoulSpeaker(this);
    }
}

class NissaWorldsoulSpeakerEffect extends ContinuousEffectImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard();

    private final AlternativeCostSourceAbility alternativeCastingCostAbility = new AlternativeCostSourceAbility(
            new PayEnergyCost(8), SourceIsSpellCondition.instance, null, filter, true
    );

    public NissaWorldsoulSpeakerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "You may pay eight {E} rather than pay the mana cost for permanent spells you cast";
    }

    private NissaWorldsoulSpeakerEffect(final NissaWorldsoulSpeakerEffect effect) {
        super(effect);
    }

    @Override
    public NissaWorldsoulSpeakerEffect copy() {
        return new NissaWorldsoulSpeakerEffect(this);
    }

    @Override
    public void init(Ability source, Game game, UUID activePlayerId) {
        super.init(source, game, activePlayerId);
        alternativeCastingCostAbility.setSourceId(source.getSourceId());
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.getAlternativeSourceCosts().add(alternativeCastingCostAbility);
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
