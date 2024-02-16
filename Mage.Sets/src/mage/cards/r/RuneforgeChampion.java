package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceIsSpellCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RuneforgeChampion extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Rune card");

    static {
        filter.add(SubType.RUNE.getPredicate());
    }

    public RuneforgeChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Runeforge Champion enters the battlefield, you may search your library and/or graveyard for a Rune card, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryGraveyardPutInHandEffect(
                filter, false, false
        ).setText("search your library and/or graveyard for a Rune card, reveal it, " +
                "and put it into your hand. If you search your library this way, shuffle"), true));

        // You may pay {1} rather than pay the mana cost for Rune spells you cast.
        this.addAbility(new SimpleStaticAbility(new RuneforgeChampionEffect()));
    }

    private RuneforgeChampion(final RuneforgeChampion card) {
        super(card);
    }

    @Override
    public RuneforgeChampion copy() {
        return new RuneforgeChampion(this);
    }
}

class RuneforgeChampionEffect extends ContinuousEffectImpl {

    private static final FilterCard filter = new FilterCard("Rune spells");

    static {
        filter.add(SubType.RUNE.getPredicate());
    }

    private final AlternativeCostSourceAbility alternativeCastingCostAbility = new AlternativeCostSourceAbility(
            new ManaCostsImpl<>("{1}"), SourceIsSpellCondition.instance, null, filter, true
    );

    RuneforgeChampionEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Detriment);
        staticText = "You may pay {1} rather than pay the mana cost for Rune spells you cast";
    }

    private RuneforgeChampionEffect(final RuneforgeChampionEffect effect) {
        super(effect);
    }

    @Override
    public RuneforgeChampionEffect copy() {
        return new RuneforgeChampionEffect(this);
    }

    @Override
    public void init(Ability source, Game game, UUID activePlayerId) {
        super.init(source, game, activePlayerId);
        alternativeCastingCostAbility.setSourceId(source.getSourceId());
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.getAlternativeSourceCosts().add(alternativeCastingCostAbility);
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
}
