package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SoulFoundry extends CardImpl {

    public SoulFoundry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Imprint - When Soul Foundry enters the battlefield, you may exile a creature card from your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SoulFoundryImprintEffect(), true)
                .setAbilityWord(AbilityWord.IMPRINT)
        );

        // {X}, {T}: Create a token that's a copy of the exiled card. X is the converted mana cost of that card.
        Ability ability = new SimpleActivatedAbility(new SoulFoundryEffect(), new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        ability.setCostAdjuster(SoulFoundryAdjuster.instance);
        this.addAbility(ability);
    }

    private SoulFoundry(final SoulFoundry card) {
        super(card);
    }

    @Override
    public SoulFoundry copy() {
        return new SoulFoundry(this);
    }
}

enum SoulFoundryAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        Permanent sourcePermanent = game.getPermanent(ability.getSourceId());
        if (sourcePermanent != null) {
            if (!sourcePermanent.getImprinted().isEmpty()) {
                Card imprinted = game.getCard(sourcePermanent.getImprinted().get(0));
                if (imprinted != null) {
                    ability.getManaCostsToPay().clear();
                    ability.getManaCostsToPay().add(0, new GenericManaCost(imprinted.getManaValue()));
                }
            }
        }

        // no {X} anymore as we already have imprinted the card with defined manacost
        for (ManaCost cost : ability.getManaCostsToPay()) {
            if (cost instanceof VariableCost) {
                cost.setPaid();
            }
        }
    }
}

class SoulFoundryImprintEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("creature card from your hand");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    SoulFoundryImprintEffect() {
        super(Outcome.Neutral);
        staticText = "you may exile a creature card from your hand";
    }

    private SoulFoundryImprintEffect(SoulFoundryImprintEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null) {
            if (!controller.getHand().isEmpty()) {
                TargetCard target = new TargetCard(Zone.HAND, filter);
                if (target.canChoose(source.getControllerId(), source, game)
                        && controller.choose(Outcome.Benefit, controller.getHand(), target, game)) {
                    Card card = controller.getHand().get(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCardToExileWithInfo(card, source.getSourceId(), sourcePermanent.getIdName() + " (Imprint)", source, game, Zone.HAND, true);
                        Permanent permanent = game.getPermanent(source.getSourceId());
                        if (permanent != null) {
                            permanent.imprint(card.getId(), game);
                            permanent.addInfo("imprint", CardUtil.addToolTipMarkTags("[Imprinted card - " + card.getLogName() + ']'), game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public SoulFoundryImprintEffect copy() {
        return new SoulFoundryImprintEffect(this);
    }
}

class SoulFoundryEffect extends OneShotEffect {

    SoulFoundryEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a token that's a copy of the exiled card. X is the mana value of that card";
    }

    private SoulFoundryEffect(final SoulFoundryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent soulFoundry = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (soulFoundry != null
                    && soulFoundry.getImprinted() != null
                    && !soulFoundry.getImprinted().isEmpty()) {
                Card imprinted = game.getCard(soulFoundry.getImprinted().get(0));
                if (imprinted != null
                        && game.getState().getZone(imprinted.getId()) == Zone.EXILED) {
                    CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
                    effect.setTargetPointer(new FixedTarget(imprinted.getId(), imprinted.getZoneChangeCounter(game)));
                    return effect.apply(game, source);
                }
            }
        }
        return false;
    }

    @Override
    public SoulFoundryEffect copy() {
        return new SoulFoundryEffect(this);
    }
}
