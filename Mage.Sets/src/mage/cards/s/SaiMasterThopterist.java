package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.permanent.token.ThopterColorlessToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author TheElk801
 */
public final class SaiMasterThopterist extends CardImpl {

    private static final FilterControlledArtifactPermanent filter2 = new FilterControlledArtifactPermanent("artifacts");

    public SaiMasterThopterist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever you cast an artifact spell, create a 1/1 colorless Thopter artifact creature token with flying.
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new ThopterColorlessToken()), StaticFilters.FILTER_SPELL_AN_ARTIFACT, false));

        // {1}{U}, Sacrifice two artifacts: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(1),
                new ManaCostsImpl<>("{1}{U}")
        );
        ability.addCost(new SacrificeTargetCost(2, filter2));
        this.addAbility(ability);
    }

    private SaiMasterThopterist(final SaiMasterThopterist card) {
        super(card);
    }

    @Override
    public SaiMasterThopterist copy() {
        return new SaiMasterThopterist(this);
    }
}
