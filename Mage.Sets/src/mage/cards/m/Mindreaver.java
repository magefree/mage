package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.TargetSpell;
import mage.util.CardUtil;

import java.util.Objects;
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
        ability = new SimpleActivatedAbility(new CounterTargetEffect(), new ManaCostsImpl("{U}{U}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new MindreaverTarget());
        this.addAbility(ability);
    }

    private Mindreaver(final Mindreaver card) {
        super(card);
    }

    @Override
    public Mindreaver copy() {
        return new Mindreaver(this);
    }
}

class MindreaverTarget extends TargetSpell {

    private static final FilterSpell filter
            = new FilterSpell("spell with the same name as a card exiled with {this}");

    MindreaverTarget() {
        super(filter);
    }

    private MindreaverTarget(final MindreaverTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Spell spell = game.getSpell(id);
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        return super.canTarget(id, source, game)
                && spell != null
                && exileZone != null
                && !exileZone.isEmpty()
                && exileZone
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .anyMatch(card -> CardUtil.haveSameNames(spell, card));
    }
}

class MindreaverExileEffect extends OneShotEffect {

    MindreaverExileEffect() {
        super(Outcome.Exile);
        this.staticText = "exile the top three cards of target player's library";
    }

    private MindreaverExileEffect(final MindreaverExileEffect effect) {
        super(effect);
    }

    @Override
    public MindreaverExileEffect copy() {
        return new MindreaverExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller == null || player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 3));
        return controller.moveCardsToExile(
                cards.getCards(game), source, game, true,
                CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source)
        );
    }
}
