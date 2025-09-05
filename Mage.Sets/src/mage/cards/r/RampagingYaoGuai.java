package mage.cards.r;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author notgreat
 */
public final class RampagingYaoGuai extends CardImpl {

    public RampagingYaoGuai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}{G}{G}");

        this.subtype.add(SubType.BEAR);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Rampaging Yao Guai enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));

        // When Rampaging Yao Guai enters the battlefield, destroy any number of target artifacts and/or enchantments with total mana value X or less.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new RampagingYaoGuaiTarget());
        this.addAbility(ability);
    }

    private RampagingYaoGuai(final RampagingYaoGuai card) {
        super(card);
    }

    @Override
    public RampagingYaoGuai copy() {
        return new RampagingYaoGuai(this);
    }
}

class RampagingYaoGuaiTarget extends TargetPermanent {

    private static final FilterPermanent filterStatic
            = new FilterArtifactOrEnchantmentPermanent("artifacts and/or enchantments with total mana value X or less");

    RampagingYaoGuaiTarget() {
        super(0, Integer.MAX_VALUE, filterStatic, false);
    }

    private RampagingYaoGuaiTarget(final RampagingYaoGuaiTarget target) {
        super(target);
    }

    @Override
    public RampagingYaoGuaiTarget copy() {
        return new RampagingYaoGuaiTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return super.canTarget(playerId, id, source, game)
                && CardUtil.checkCanTargetTotalValueLimit(
                this.getTargets(), id, MageObject::getManaValue, GetXValue.instance.calculate(game, source, null), game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        return CardUtil.checkPossibleTargetsTotalValueLimit(this.getTargets(),
                super.possibleTargets(sourceControllerId, source, game),
                MageObject::getManaValue, GetXValue.instance.calculate(game, source, null), game);
    }

    @Override
    public String getMessage(Game game) {
        // shows selected total
        int selectedValue = this.getTargets().stream()
                .map(game::getObject)
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum();
        return super.getMessage(game) + " (selected total mana value " + selectedValue + ")";
    }
}
