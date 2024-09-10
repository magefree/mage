package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class BehemothOfVault extends CardImpl {

    public BehemothOfVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Behemoth of Vault 0 enters the battlefield, you get {E}{E}{E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(4)));

        // When Behemoth of Vault 0 dies, you may pay an amount of {E} equal to target nonland permanent's mana value. When you do, destroy that permanent.
        Ability ability = new DiesSourceTriggeredAbility(new BehemothOfVaultDoWhenCostPaid(
                new ReflexiveTriggeredAbility(new DestroyTargetEffect().setText("destroy that permanent"), false)));
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);
    }

    private BehemothOfVault(final BehemothOfVault card) {
        super(card);
    }

    @Override
    public BehemothOfVault copy() {
        return new BehemothOfVault(this);
    }
}

//Based on DoWhenCostPaid but constructs the cost itself and uses a fixed target for the effect
class BehemothOfVaultDoWhenCostPaid extends OneShotEffect {

    private final ReflexiveTriggeredAbility ability;

    BehemothOfVaultDoWhenCostPaid(ReflexiveTriggeredAbility ability) {
        super(Outcome.Benefit);
        this.ability = ability;
        this.staticText = "you may pay an amount of {E} equal to target nonland permanent's mana value. When you do, destroy that permanent.";
    }

    BehemothOfVaultDoWhenCostPaid(final BehemothOfVaultDoWhenCostPaid effect) {
        super(effect);
        this.ability = effect.ability.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        if (targetPermanent == null || player == null) {
            return false;
        }
        Cost cost = new PayEnergyCost(targetPermanent.getManaValue());
        if (!cost.canPay(source, source, player.getId(), game)
                || !player.chooseUse(Outcome.DestroyPermanent, cost.getText(), source, game)) {
            return true;
        }
        cost.clearPaid();
        int bookmark = game.bookmarkState();
        if (cost.pay(source, game, source, player.getId(), false)) {
            ability.getEffects().setTargetPointer(new FixedTarget(targetPermanent, game));
            game.fireReflexiveTriggeredAbility(ability, source);
            player.resetStoredBookmark(game);
            return true;
        }
        player.restoreState(bookmark, BehemothOfVaultDoWhenCostPaid.class.getName(), game);
        return true;
    }

    @Override
    public void setValue(String key, Object value) {
        super.setValue(key, value);
        ability.getEffects().setValue(key, value);
    }

    @Override
    public BehemothOfVaultDoWhenCostPaid copy() {
        return new BehemothOfVaultDoWhenCostPaid(this);
    }
}
