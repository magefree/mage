package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.combat.CombatGroup;

/**
 *
 * @author Plopman
 */
public final class QasaliAmbusher extends CardImpl {

    public QasaliAmbusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // If a creature is attacking you and you control a Forest and a Plains, 
        // you may cast Qasali Ambusher without paying its mana cost and as though it had flash.
        this.addAbility(new QasaliAmbusherAbility());

    }

    public QasaliAmbusher(final QasaliAmbusher card) {
        super(card);
    }

    @Override
    public QasaliAmbusher copy() {
        return new QasaliAmbusher(this);
    }
}

class QasaliAmbusherAbility extends ActivatedAbilityImpl {

    private static final FilterControlledLandPermanent filterPlains = new FilterControlledLandPermanent();
    private static final FilterControlledLandPermanent filterForest = new FilterControlledLandPermanent();

    static {
        filterPlains.add(SubType.PLAINS.getPredicate());
        filterForest.add(SubType.FOREST.getPredicate());
    }

    public QasaliAmbusherAbility() {
        super(Zone.HAND, new CastAsThoughItHadFlashSourceEffect(Duration.EndOfGame), new ManaCostsImpl());
        this.timing = TimingRule.INSTANT;
        this.usesStack = false;
    }

    public QasaliAmbusherAbility(final QasaliAmbusherAbility ability) {
        super(ability);
    }

    @Override
    public QasaliAmbusherAbility copy() {
        return new QasaliAmbusherAbility(this);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        if (!game.getBattlefield().getActivePermanents(filterPlains,
                this.getControllerId(), this.getSourceId(), game).isEmpty()
                && !game.getBattlefield().getActivePermanents(filterForest,
                        this.getControllerId(), this.getSourceId(), game).isEmpty()) {
            for (CombatGroup group : game.getCombat().getGroups()) {
                if (isControlledBy(group.getDefenderId())) {
                    return super.canActivate(playerId, game);
                }
            }
        }
        return ActivationStatus.getFalse();
    }

    @Override
    public String getRule(boolean all) {
        return this.getRule();
    }

    @Override
    public String getRule() {
        return "If a creature is attacking you and you control a Forest and "
                + "a Plains, you may cast {this} without paying its mana "
                + "cost and as though it had flash.";
    }
}
