package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.token.TokenImpl;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MobilizedDistrict extends CardImpl {

    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent();

    static {
        filter.add(new SupertypePredicate(SuperType.LEGENDARY));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    static final DynamicValue cardsCount = new PermanentsOnBattlefieldCount(filter);

    public MobilizedDistrict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {4}: Mobilized District becomes a 3/3 Citizen creature with vigilance until end of turn. It's still a land. This ability costs {1} less to activate for each legendary creature and planeswalker you control.
        // TODO: Make ability properly copiable
        Ability ability = new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new MobilizedDistrictToken(), "land", Duration.EndOfTurn
        ).setText("{this} becomes a 3/3 Citizen creature with vigilance until end of turn. " +
                "It's still a land. This ability costs {1} less to activate " +
                "for each legendary creature and planeswalker you control."
        ), new GenericManaCost(4));
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new MobilizedDistrictCostIncreasingEffect(ability.getOriginalId())
        ).addHint(new ValueHint("Legendary creatures and planeswalkers you control", cardsCount)));
    }

    private MobilizedDistrict(final MobilizedDistrict card) {
        super(card);
    }

    @Override
    public MobilizedDistrict copy() {
        return new MobilizedDistrict(this);
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

class MobilizedDistrictCostIncreasingEffect extends CostModificationEffectImpl {

    private final UUID originalId;

    MobilizedDistrictCostIncreasingEffect(UUID originalId) {
        super(Duration.EndOfGame, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.originalId = originalId;
    }

    private MobilizedDistrictCostIncreasingEffect(final MobilizedDistrictCostIncreasingEffect effect) {
        super(effect);
        this.originalId = effect.originalId;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int count = MobilizedDistrict.cardsCount.calculate(game, source, this);
            CardUtil.reduceCost(abilityToModify, count);
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getOriginalId().equals(originalId);
    }

    @Override
    public MobilizedDistrictCostIncreasingEffect copy() {
        return new MobilizedDistrictCostIncreasingEffect(this);
    }
}
