package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.permanent.token.TokenImpl;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MobilizedDistrict extends CardImpl {

    public MobilizedDistrict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {4}: Mobilized District becomes a 3/3 Citizen creature with vigilance until end of turn. It's still a land. This ability costs {1} less to activate for each legendary creature and planeswalker you control.
        Ability ability = new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new MobilizedDistrictToken(), CardType.LAND, Duration.EndOfTurn
        ).setText("{this} becomes a 3/3 Citizen creature with vigilance until end of turn. " +
                "It's still a land. This ability costs {1} less to activate " +
                "for each legendary creature and planeswalker you control."
        ), new GenericManaCost(4));
        ability.setCostAdjuster(MobilizedDistrictAdjuster.instance);
        this.addAbility(ability.addHint(MobilizedDistrictAdjuster.getHint()));
    }

    private MobilizedDistrict(final MobilizedDistrict card) {
        super(card);
    }

    @Override
    public MobilizedDistrict copy() {
        return new MobilizedDistrict(this);
    }
}

enum MobilizedDistrictAdjuster implements CostAdjuster {
    instance;

    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent();

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    static final DynamicValue cardsCount = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Legendary creatures and planeswalkers you control", cardsCount);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller != null) {
            int count = cardsCount.calculate(game, ability, null);
            CardUtil.reduceCost(ability, count);
        }
    }
}

class MobilizedDistrictToken extends TokenImpl {

    MobilizedDistrictToken() {
        super("", "3/3 Citizen creature with vigilance");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.CITIZEN);
        power = new MageInt(3);
        toughness = new MageInt(3);
        addAbility(VigilanceAbility.getInstance());
    }

    private MobilizedDistrictToken(final MobilizedDistrictToken token) {
        super(token);
    }

    public MobilizedDistrictToken copy() {
        return new MobilizedDistrictToken(this);
    }
}
