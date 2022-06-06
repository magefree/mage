package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
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
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class NomadMythmaker extends CardImpl {

    private static final FilterCard FILTER = new FilterCard("Aura card from a graveyard");

    static {
        FILTER.add(CardType.ENCHANTMENT.getPredicate());
        FILTER.add(SubType.AURA.getPredicate());
    }

    public NomadMythmaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOMAD);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {W}, {tap}: Put target Aura card from a graveyard onto the battlefield under your control attached to a creature you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new NomadMythmakerEffect(), new ManaCostsImpl<>("{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInGraveyard(1, FILTER));
        this.addAbility(ability);

    }

    private NomadMythmaker(final NomadMythmaker card) {
        super(card);
    }

    @Override
    public NomadMythmaker copy() {
        return new NomadMythmaker(this);
    }
}

class NomadMythmakerEffect extends OneShotEffect {

    public NomadMythmakerEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Put target Aura card from a graveyard onto the battlefield under your control attached to a creature you control.";
    }

    public NomadMythmakerEffect(final NomadMythmakerEffect effect) {
        super(effect);
    }

    @Override
    public NomadMythmakerEffect copy() {
        return new NomadMythmakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card aura = game.getCard(source.getFirstTarget());
        if (controller == null || aura == null) {
            return false;
        }
        FilterControlledCreaturePermanent FILTER = new FilterControlledCreaturePermanent("Choose a creature you control");
        TargetControlledPermanent target = new TargetControlledPermanent(FILTER);
        target.setNotTarget(true);
        if (controller.choose(Outcome.PutCardInPlay, target, source, game)) {
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null
                    && !permanent.cantBeAttachedBy(aura, source, game, false)) {
                game.getState().setValue("attachTo:" + aura.getId(), permanent);
                controller.moveCards(aura, Zone.BATTLEFIELD, source, game);
                return permanent.addAttachment(aura.getId(), source, game);
            }
        }
        return false;
    }
}
