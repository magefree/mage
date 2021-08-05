package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AberrantMindSorcerer extends CardImpl {

    public AberrantMindSorcerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Psionic Spells — When Aberrant Mind Sorcerer enters the battlefield, choose target instant or sorcery card in your graveyard, then roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect(
                20, "choose target instant or sorcery card in your graveyard, then roll a d20"
        );
        Ability ability = new EntersBattlefieldTriggeredAbility(effect);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability.withFlavorWord("Psionic Spells"));

        // 1-9 | You may put that card on top of your library.
        effect.addTableEntry(1, 9, new AberrantMindSorcererEffect());

        // 10-20 | Return that card to your hand.
        effect.addTableEntry(10, 20, new ReturnFromGraveyardToHandTargetEffect().setText("return that card to your hand"));
    }

    private AberrantMindSorcerer(final AberrantMindSorcerer card) {
        super(card);
    }

    @Override
    public AberrantMindSorcerer copy() {
        return new AberrantMindSorcerer(this);
    }
}

class AberrantMindSorcererEffect extends OneShotEffect {

    AberrantMindSorcererEffect() {
        super(Outcome.Benefit);
        staticText = "you may put that card on top of your library";
    }

    private AberrantMindSorcererEffect(final AberrantMindSorcererEffect effect) {
        super(effect);
    }

    @Override
    public AberrantMindSorcererEffect copy() {
        return new AberrantMindSorcererEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        return player != null
                && card != null
                && player.chooseUse(
                outcome, "Put " + card.getName() + " on top of your library?", source, game
        ) && player.putCardsOnTopOfLibrary(card, game, source, false);
    }
}
