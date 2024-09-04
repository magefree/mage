package mage.cards.t;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DraftFromSpellbookThenExileCastThisTurnEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DevilToken;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author Sidorovich77
 */
public final class TibaltWickedTormentor extends CardImpl {

    private static final List<String> spellbook = Collections.unmodifiableList(Arrays.asList(
            "Brimstone Vandal",
            "Chained Brute",
            "Charmbreaker Devils",
            "Devil's Play",
            "Festival Crasher",
            "Forge Devil",
            "Frenzied Devils",
            "Havoc Jester",
            "Hellrider",
            "Hobblefiend",
            "Pitchburn Devils",
            "Sin Prodder",
            "Spiteful Prankster",
            "Tibalt's Rager",
            "Torch Fiend"
    ));

    public TibaltWickedTormentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIBALT);

        this.setStartingLoyalty(3);

        // +1: Add {R}{R}. Draft a card from Tibalt, Wicked Tormentor’s spellbook, then exile it. Until end of turn, you may cast that card.
        Ability ability = new LoyaltyAbility(new BasicManaEffect(Mana.RedMana(2)), 1);
        ability.addEffect(new DraftFromSpellbookThenExileCastThisTurnEffect(spellbook));
        this.addAbility(ability);

        // +1: Tibalt, Wicked Tormentor deals 4 damage to target creature or planeswalker unless its controller has Tibalt deal 4 damage to them.
        // If they do, you may discard a card. If you do, draw a card.
        Ability ability1 = new LoyaltyAbility(new TibaltDamageEffect(), 1);
        ability1.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(ability1);

        // -X: Create X 1/1 red Devil creature tokens with “When this creature dies, it deals 1 damage to any target.”
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new DevilToken(), GetXValue.instance)));
    }

    private TibaltWickedTormentor(final TibaltWickedTormentor card) {
        super(card);
    }

    @Override
    public TibaltWickedTormentor copy() {
        return new TibaltWickedTormentor(this);
    }
}

class TibaltDamageEffect extends OneShotEffect {

    TibaltDamageEffect() {
        super(Outcome.Neutral);
        this.staticText = " {this} deals 4 damage to target creature or planeswalker unless its controller has Tibalt deal 4 damage to them." +
                " If they do, you may discard a card. If you do, draw a card.";
    }

    private TibaltDamageEffect(final TibaltDamageEffect effect) {
        super(effect);
    }

    @Override
    public TibaltDamageEffect copy() {
        return new TibaltDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        Player controller = game.getPlayer(permanent.getControllerId());
        if (player == null || controller == null) {
            String message = "Have Tibalt, Wicked Tormentor do 4 damage to you?";
            if (controller.chooseUse(Outcome.Damage, message, source, game)) {
                controller.damage(4, source.getSourceId(), source, game);
                Effect effect = new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost());
                effect.apply(game, source);
            } else {
                permanent.damage(4, source, game);
            }
            return true;
        }
        return false;
    }
}

