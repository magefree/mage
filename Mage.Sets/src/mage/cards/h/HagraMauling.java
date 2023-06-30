package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HagraMauling extends ModalDoubleFacedCard {

    private static final Hint hint = new ConditionHint(
            HagraMaulingCondition.instance, "An opponent controls no basic lands"
    );

    public HagraMauling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.INSTANT}, new SubType[]{}, "{2}{B}{B}",
                "Hagra Broodpit", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Hagra Mauling
        // Instant

        // This spell costs {1} less to cast if an opponent controls no basic lands.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(1, HagraMaulingCondition.instance).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Destroy target creature.
        this.getLeftHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getLeftHalfCard().getSpellAbility().addHint(hint);

        // 2.
        // Hagra Broodpit
        // Land

        // Hagra Broodpit enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B}.
        this.getRightHalfCard().addAbility(new BlackManaAbility());
    }

    private HagraMauling(final HagraMauling card) {
        super(card);
    }

    @Override
    public HagraMauling copy() {
        return new HagraMauling(this);
    }
}

enum HagraMaulingCondition implements Condition {
    instance;

    private static final FilterPermanent filter = new FilterControlledLandPermanent();

    static {
        filter.add(SuperType.BASIC.getPredicate());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null && game.getBattlefield().count(filter, playerId, source, game) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "an opponent controls no basic lands";
    }
}
