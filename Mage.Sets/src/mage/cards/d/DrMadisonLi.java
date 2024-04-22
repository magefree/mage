package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DrMadisonLi extends CardImpl {

    public DrMadisonLi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast an artifact spell, you get {E}.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new GetEnergyCountersControllerEffect(1),
                StaticFilters.FILTER_SPELL_AN_ARTIFACT, false
        ));

        // {T}, Pay {E}: Target creature gets +1/+0 and gains trample and haste until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostTargetEffect(1, 0).setText("target creature gets +1/+0"), new TapSourceCost()
        );
        ability.addCost(new PayEnergyCost(1));
        ability.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance()).setText("and gains trample"));
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance()).setText("and haste until end of turn"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {T}, Pay {E}{E}{E}: Draw a card.
        ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new TapSourceCost());
        ability.addCost(new PayEnergyCost(3));
        this.addAbility(ability);

        // {T}, Pay {E}{E}{E}{E}{E}: Return target artifact card from your graveyard to the battlefield tapped.
        ability = new SimpleActivatedAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(true), new TapSourceCost());
        ability.addCost(new PayEnergyCost(5));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private DrMadisonLi(final DrMadisonLi card) {
        super(card);
    }

    @Override
    public DrMadisonLi copy() {
        return new DrMadisonLi(this);
    }
}
