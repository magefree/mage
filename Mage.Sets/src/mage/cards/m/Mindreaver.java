package mage.cards.m;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.TargetSpell;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Mindreaver extends CardImpl {

    public Mindreaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // <i>Heroic</i> &mdash; Whenever you cast a spell that targets Mindreaver, exile the top three cards of target player's library.
        Ability ability = new HeroicAbility(new MindreaverExileEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // {U}{U}, Sacrifice Mindreaver: Counter target spell with the same name as a card exiled with Mindreaver.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new ManaCostsImpl("{U}{U}"));
        FilterSpell filter = new FilterSpell("spell with the same name as a card exiled with {this}");
        filter.add(new MindreaverNamePredicate(this.getId()));
        ability.addTarget(new TargetSpell(filter));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public Mindreaver(final Mindreaver card) {
        super(card);
    }

    @Override
    public Mindreaver copy() {
        return new Mindreaver(this);
    }
}

class MindreaverExileEffect extends OneShotEffect {

    public MindreaverExileEffect() {
        super(Outcome.Exile);
        this.staticText = "exile the top three cards of target opponent's library";
    }

    public MindreaverExileEffect(final MindreaverExileEffect effect) {
        super(effect);
    }

    @Override
    public MindreaverExileEffect copy() {
        return new MindreaverExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID exileId = CardUtil.getCardExileZoneId(game, source);
        MageObject sourceObject = source.getSourceObject(game);
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (opponent != null && sourceObject != null) {
            for (int i = 0; i < 3; i++) {
                Card card = opponent.getLibrary().getFromTop(game);
                if (card != null) {
                    card.moveToExile(exileId, sourceObject.getIdName(), source, game);
                }
            }
        }
        return false;
    }
}

class MindreaverNamePredicate implements Predicate<MageObject> {

    private final UUID sourceId;

    public MindreaverNamePredicate(UUID sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        Set<String> cardNames = new HashSet<>();
        UUID exileId = CardUtil.getCardExileZoneId(game, sourceId);
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        if (exileZone != null) {
            for (Card card : exileZone.getCards(game)) {
                cardNames.add(card.getName());
            }
        }
        return cardNames.stream().anyMatch(needName -> CardUtil.haveSameNames(input, needName, game));
    }

    @Override
    public String toString() {
        return "spell with the same name as a card exiled with {this}";
    }
}
