package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class GlissasRetriever extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public GlissasRetriever(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");
        this.subtype.add(SubType.PHYREXIAN, SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Toxic 3
        this.addAbility(new ToxicAbility(3));

        // Glissa's Retriever can't be blocked by creatures with power 2 or less.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

        // Corrupted — When Glissa's Retriever dies, exile it. When you do, return up to X target cards from your
        // graveyard to your hand, where X is the number of opponents who have three or more poison counters.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new ReturnFromGraveyardToHandTargetEffect().setText("return up to X target cards from your graveyard to your hand, " +
                        "where X is the number of opponents who have three or more poison counters"), false);
        ability.setTargetAdjuster(GlissasRetrieverAdjuster.instance);
        this.addAbility(new DiesSourceTriggeredAbility(new DoWhenCostPaid(
                ability, new ExileSourceFromGraveCost().setText("exile it"), null, false
        )).setAbilityWord(AbilityWord.CORRUPTED));
    }

    private GlissasRetriever(final GlissasRetriever card) {
        super(card);
    }

    @Override
    public GlissasRetriever copy() {
        return new GlissasRetriever(this);
    }
}

enum GlissasRetrieverAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int amount = 0;
        for (UUID opponentId : game.getOpponents(ability.getControllerId(), true)) {
            Player player = game.getPlayer(opponentId);
            if (player != null && player.getCounters().getCount(CounterType.POISON) >= 3) {
                amount++;
            }
        }
        ability.addTarget(new TargetCardInYourGraveyard(0, amount));
    }
}
