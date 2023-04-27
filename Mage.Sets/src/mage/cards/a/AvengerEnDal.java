package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AvengerEnDal extends CardImpl {

    public AvengerEnDal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{W}, {T}, Discard a card: Exile target attacking creature. Its controller gains life equal to its toughness.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetAttackingCreature());
        ability.addEffect(new AvengerEnDalEffect());
        this.addAbility(ability);
    }

    private AvengerEnDal(final AvengerEnDal card) {
        super(card);
    }

    @Override
    public AvengerEnDal copy() {
        return new AvengerEnDal(this);
    }
}

class AvengerEnDalEffect extends OneShotEffect {

    public AvengerEnDalEffect() {
        super(Outcome.GainLife);
        staticText = "Its controller gains life equal to its toughness";
    }

    public AvengerEnDalEffect(final AvengerEnDalEffect effect) {
        super(effect);
    }

    @Override
    public AvengerEnDalEffect copy() {
        return new AvengerEnDalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                player.gainLife(permanent.getToughness().getValue(), game, source);
            }
            return true;
        }
        return false;
    }
}