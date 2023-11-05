package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceAttackingAloneCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiderToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class ThijarianWitness extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("another creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(AttackBlockAlonePredicate.instance);
    }

    public ThijarianWitness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        DiesCreatureTriggeredAbility ability = new DiesCreatureTriggeredAbility(
                new ExileTargetEffect().setText("if it was attacking or blocking alone, exile it and investigate"),
                false, filter, true
        );
        ability.addEffect(new InvestigateEffect());
        ability.withFlavorWord("Bear Witness");

        this.addAbility(ability);
    }

    private ThijarianWitness(final ThijarianWitness card) {
        super(card);
    }

    @Override
    public ThijarianWitness copy() {
        return new ThijarianWitness(this);
    }
}
enum AttackBlockAlonePredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        if (input.isAttacking()){
            return game.getCombat().attacksAlone();
        } else if (input.getBlocking() > 0){
            return game.getCombat().getBlockers().size() == 1;
        }
        return false;
    }

    @Override
    public String toString() {
        return "attacking or blocking alone";
    }
}
