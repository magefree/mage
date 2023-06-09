package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class KazuulsTollCollector extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.EQUIPMENT, "Equipment you control");

    public KazuulsTollCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {0}: Attach target Equipment you control to Kazuul's Toll Collector. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new KazuulsTollCollectorEffect(), new GenericManaCost(0));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private KazuulsTollCollector(final KazuulsTollCollector card) {
        super(card);
    }

    @Override
    public KazuulsTollCollector copy() {
        return new KazuulsTollCollector(this);
    }
}

class KazuulsTollCollectorEffect extends OneShotEffect {

    public KazuulsTollCollectorEffect() {
        super(Outcome.BoostCreature);
        staticText = "attach target Equipment you control to {this}";
    }

    public KazuulsTollCollectorEffect(final KazuulsTollCollectorEffect effect) {
        super(effect);
    }

    @Override
    public KazuulsTollCollectorEffect copy() {
        return new KazuulsTollCollectorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent != null && equipment != null
                && permanent.addAttachment(equipment.getId(), source, game);
    }
}
