package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.FullPartyCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheDestinedBlackMage extends CardImpl {

    public TheDestinedBlackMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // {B}, {T}: Another target creature you control gains deathtouch until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(DeathtouchAbility.getInstance()), new ManaCostsImpl<>("{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);

        // Whenever you cast a noncreature spell, The Destined Black Mage deals 1 damage to each opponent. If you have a full party, it deals 3 damage to each opponent instead.
        this.addAbility(new SpellCastControllerTriggeredAbility(new ConditionalOneShotEffect(
                new DamagePlayersEffect(3, TargetController.OPPONENT),
                new DamagePlayersEffect(1, TargetController.OPPONENT),
                FullPartyCondition.instance, "{this} deals 1 damage to each opponent. " +
                "If you have a full party, it deals 3 damage to each opponent instead"
        ), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false).addHint(PartyCountHint.instance));
    }

    private TheDestinedBlackMage(final TheDestinedBlackMage card) {
        super(card);
    }

    @Override
    public TheDestinedBlackMage copy() {
        return new TheDestinedBlackMage(this);
    }
}
