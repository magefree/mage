package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HowlpackPiper extends CardImpl {

    public HowlpackPiper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.secondSideCardClazz = mage.cards.w.WildsongHowler.class;

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // {1}{G}, {T}: You may put a creature card from your hand onto the battlefield. If it's a Wolf or Werewolf, untap Howlpack Piper. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new HowlpackPiperEffect(), new ManaCostsImpl<>("{1}{G}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Daybound
        this.addAbility(new DayboundAbility());
    }

    private HowlpackPiper(final HowlpackPiper card) {
        super(card);
    }

    @Override
    public HowlpackPiper copy() {
        return new HowlpackPiper(this);
    }
}

class HowlpackPiperEffect extends OneShotEffect {

    HowlpackPiperEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "you may put a creature card from your hand onto the battlefield. " +
                "If it's a Wolf or Werewolf, untap {this}";
    }

    private HowlpackPiperEffect(final HowlpackPiperEffect effect) {
        super(effect);
    }

    @Override
    public HowlpackPiperEffect copy() {
        return new HowlpackPiperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getHand().count(StaticFilters.FILTER_CARD_CREATURE, game) < 1) {
            return false;
        }
        TargetCard target = new TargetCardInHand(0, 1, StaticFilters.FILTER_CARD_CREATURE);
        player.choose(outcome, player.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(card.getId());
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || sourcePermanent == null) {
            return true;
        }
        if (permanent.hasSubtype(SubType.WOLF, game) || permanent.hasSubtype(SubType.WEREWOLF, game)) {
            sourcePermanent.untap(game);
        }
        return true;
    }
}
