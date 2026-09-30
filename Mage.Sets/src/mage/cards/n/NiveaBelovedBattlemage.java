package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.RemoveFromCombatTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.AkromaAngelOfWrathToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class NiveaBelovedBattlemage extends CardImpl {

    public NiveaBelovedBattlemage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever a creature you control becomes blocked, you may untap it and remove it from combat.
        Ability ability = new BecomesBlockedAllTriggeredAbility(
            new UntapTargetEffect().setText("you may untap it"),
            true,
            StaticFilters.FILTER_CONTROLLED_CREATURE,
            true
        );
        ability.addEffect(new RemoveFromCombatTargetEffect().setText("and remove it from combat"));
        this.addAbility(ability);

        // {5}{W}{W}{W}, Sacrifice Nivea: Create an Akroma, Angel of Wrath token.
        Ability ability2 = new SimpleActivatedAbility(
            new CreateTokenEffect(new AkromaAngelOfWrathToken()),
            new ManaCostsImpl<>("{5}{W}{W}{W}")
        );
        ability2.addCost(new SacrificeSourceCost());
        this.addAbility(ability2);
    }

    private NiveaBelovedBattlemage(final NiveaBelovedBattlemage card) {
        super(card);
    }

    @Override
    public NiveaBelovedBattlemage copy() {
        return new NiveaBelovedBattlemage(this);
    }
}
