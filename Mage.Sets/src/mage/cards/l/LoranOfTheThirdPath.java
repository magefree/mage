package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author weirddan455
 */
public final class LoranOfTheThirdPath extends CardImpl {

    public LoranOfTheThirdPath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Loran of the Third Path enters the battlefield, destroy up to one target artifact or enchantment.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);

        // {T}: You and target opponent each draw a card.
        ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1).setText("you"), new TapSourceCost());
        ability.addEffect(new DrawCardTargetEffect(1).setText("and target opponent each draw a card"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private LoranOfTheThirdPath(final LoranOfTheThirdPath card) {
        super(card);
    }

    @Override
    public LoranOfTheThirdPath copy() {
        return new LoranOfTheThirdPath(this);
    }
}
