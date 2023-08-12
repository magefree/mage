package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;
import mage.ApprovingObject;

/**
 * @author LevelX2
 */
public final class EliteArcanist extends CardImpl {

    public EliteArcanist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Elite Arcanist enters the battlefield, you may exile an instant card from your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EliteArcanistImprintEffect(), true));

        // {X}, {T}: Copy the exiled card. You may cast the copy without paying its mana cost. X is the converted mana cost of the exiled card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new EliteArcanistCopyEffect(), new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        ability.setCostAdjuster(EliteArcanistAdjuster.instance);
        this.addAbility(ability);
    }

    private EliteArcanist(final EliteArcanist card) {
        super(card);
    }

    @Override
    public EliteArcanist copy() {
        return new EliteArcanist(this);
    }
}

enum EliteArcanistAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        Permanent sourcePermanent = game.getPermanent(ability.getSourceId());
        if (sourcePermanent == null
                || sourcePermanent.getImprinted() == null
                || sourcePermanent.getImprinted().isEmpty()) {
            return;
        }
        Card imprintedInstant = game.getCard(sourcePermanent.getImprinted().get(0));
        if (imprintedInstant == null) {
            return;
        }
        int cmc = imprintedInstant.getManaValue();
        if (cmc > 0) {
            ability.getManaCostsToPay().clear();
            ability.getManaCostsToPay().add(new GenericManaCost(cmc));
        }
    }
}

class EliteArcanistImprintEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("instant card from your hand");

    static {
        filter.add(CardType.INSTANT.getPredicate());
    }

    public EliteArcanistImprintEffect() {
        super(Outcome.Benefit);
        staticText = "you may exile an instant card from your hand";
    }

    public EliteArcanistImprintEffect(EliteArcanistImprintEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && !player.getHand().isEmpty()) {
            TargetCard target = new TargetCard(Zone.HAND, filter);
            if (target.canChoose(source.getControllerId(), source, game)
                    && player.choose(Outcome.Benefit, player.getHand(), target, source, game)) {
                Card card = player.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    card.moveToExile(source.getSourceId(), "Elite Arcanist", source, game);
                    Permanent permanent = game.getPermanent(source.getSourceId());
                    if (permanent != null) {
                        permanent.imprint(card.getId(), game);
                        permanent.addInfo("imprint", "[Exiled card - " + card.getName() + ']', game);
                    }
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public EliteArcanistImprintEffect copy() {
        return new EliteArcanistImprintEffect(this);
    }

}

class EliteArcanistCopyEffect extends OneShotEffect {

    public EliteArcanistCopyEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Copy the exiled card. You may cast the copy "
                + "without paying its mana cost. X is the mana value of the exiled card";
    }

    public EliteArcanistCopyEffect(final EliteArcanistCopyEffect effect) {
        super(effect);
    }

    @Override
    public EliteArcanistCopyEffect copy() {
        return new EliteArcanistCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (sourcePermanent != null
                && sourcePermanent.getImprinted() != null
                && !sourcePermanent.getImprinted().isEmpty()) {
            Card imprintedInstant = game.getCard(sourcePermanent.getImprinted().get(0));
            if (imprintedInstant != null
                    && game.getState().getZone(imprintedInstant.getId()) == Zone.EXILED) {
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    Card copiedCard = game.copyCard(imprintedInstant, source, source.getControllerId());
                    if (copiedCard != null) {
                        if (controller.chooseUse(Outcome.PlayForFree, "Cast the copied card without paying mana cost?", source, game)) {
                            game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
                            Boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(copiedCard, game, true),
                                    game, true, new ApprovingObject(source, game));
                            game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
                            return cardWasCast;
                        }
                    }
                }
            }
        }
        return false;
    }
}
