package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.*;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class KyloRen extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsPredicate.instance);
    }

    public KyloRen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SITH);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Kylo Ren attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // Whenever Kylo Ren attacks, it gets +1/+0 for each creature in your graveyard and you may tap target creature defending player controls.
        CardsInControllerGraveyardCount value = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE);
        Effect effect = new BoostSourceEffect(value, StaticValue.get(0), Duration.WhileOnBattlefield);
        effect.setText("it gets +1/+0 for each creature in your graveyard");
        Ability ability = new AttacksTriggeredAbility(effect, false);
        ability.addEffect(new KyloRenTapTargetEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 1, filter, false));
        this.addAbility(ability);
    }

    private KyloRen(final KyloRen card) {
        super(card);
    }

    @Override
    public KyloRen copy() {
        return new KyloRen(this);
    }
}

class KyloRenTapTargetEffect extends TapTargetEffect {

    public KyloRenTapTargetEffect() {
        super();
        this.staticText = "and you may tap target creature defending player controls";
    }

    public KyloRenTapTargetEffect(final KyloRenTapTargetEffect effect) {
        super(effect);
    }

    @Override
    public KyloRenTapTargetEffect copy() {
        return new KyloRenTapTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if(player != null && permanent != null) {
            if(player.chooseUse(outcome, "Tap target creature defending player controls (" + permanent.getLogName() + ")", source, game)) {
                super.apply(game, source);
            }
        }
        return false;
    }
}
