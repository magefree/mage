package mage.cards.w;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WizardsSpellbook extends CardImpl {

    public WizardsSpellbook(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}{U}{U}");

        // {T}: Exile target instant or sorcery card from a graveyard. Roll a d20. Activate only as a sorcery.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect(
                20, "exile target instant or sorcery card " +
                "from a graveyard. Roll a d20. Activate only as a sorcery"
        );
        Ability ability = new SimpleActivatedAbility(effect, new TapSourceCost());
        ability.addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY));
        this.addAbility(ability);

        // 1-9 | Copy that card. You may cast the copy.
        effect.addTableEntry(1, 9, new WizardsSpellbookEffect(1));

        // 10-19 | Copy that card. You may cast the copy by paying {1} rather than paying its mana cost.
        effect.addTableEntry(10, 19, new WizardsSpellbookEffect(2));

        // 20 | Copy each card exiled with Wizard's Spellbook. You may cast any number of the copies without paying their mana costs.
        effect.addTableEntry(20, 20, new WizardsSpellbookEffect(3));
    }

    private WizardsSpellbook(final WizardsSpellbook card) {
        super(card);
    }

    @Override
    public WizardsSpellbook copy() {
        return new WizardsSpellbook(this);
    }
}

class WizardsSpellbookEffect extends OneShotEffect {

    private final int level;

    WizardsSpellbookEffect(int level) {
        super(Outcome.Benefit);
        this.level = level;
        switch (level) {
            case 1:
                staticText = "copy that card. You may cast the copy";
                break;
            case 2:
                staticText = "copy that card. You may cast the copy by paying {1} rather than paying its mana cost";
                break;
            case 3:
                staticText = "copy each card exiled with {this}. You may cast any number of the copies without paying their mana costs";
                break;
            default:
                throw new IllegalArgumentException("Level must be 1-3");
        }
    }

    private WizardsSpellbookEffect(final WizardsSpellbookEffect effect) {
        super(effect);
        this.level = effect.level;
    }

    @Override
    public WizardsSpellbookEffect copy() {
        return new WizardsSpellbookEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(game, source);
        player.moveCardsToExile(card, source, game, true, exileId, CardUtil.getSourceName(game, source));
        if (level < 3) {
            Card copiedCard = game.copyCard(card, source, source.getControllerId());
            if (!player.chooseUse(
                    Outcome.Benefit, "Cast " + copiedCard.getName()
                            + (level == 1 ? "?" : " by paying {1}?"), source, game)
            ) {
                return false;
            }
            SpellAbility spellAbility = player.chooseAbilityForCast(copiedCard, game, level == 2);
            if (spellAbility == null) {
                return false;
            }
            game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
            if (level == 2) {
                player.setCastSourceIdWithAlternateMana(copiedCard.getId(), new ManaCostsImpl<>("{1}"), null);
            }
            player.cast(spellAbility, game, false, new ApprovingObject(source, game));
            game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
            return true;
        }
        ExileZone exile = game.getExile().getExileZone(exileId);
        if (exile == null || exile.isEmpty()) {
            return true;
        }
        Set<Card> cards = new HashSet<>();
        for (Card exiledCard : exile.getCards(game)) {
            cards.add(game.copyCard(exiledCard, source, source.getControllerId()));
        }
        while (!cards.isEmpty()) {
            for (Card copiedCard : cards) {
                if (!player.chooseUse(
                        Outcome.PlayForFree, "Cast " + copiedCard.getName()
                                + " without paying its mana cost?", source, game
                )) {
                    continue;
                }
                game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
                player.cast(
                        player.chooseAbilityForCast(copiedCard, game, true),
                        game, true, new ApprovingObject(source, game)
                );
                game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
            }
            if (!player.chooseUse(Outcome.Neutral, "Continue casting exiled cards?", source, game)) {
                break;
            }
            cards.removeIf(c -> game.getState().getZone(c.getId()) != Zone.EXILED);
        }
        return true;
    }
}
