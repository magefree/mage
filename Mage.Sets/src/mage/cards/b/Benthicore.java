
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.MerfolkWizardToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Loki
 */
public final class Benthicore extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Merfolk you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.MERFOLK.getPredicate());
    }

    public Benthicore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{U}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        
        // When Benthicore enters the battlefield, create two 1/1 blue Merfolk Wizard creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MerfolkWizardToken(), 2), false));
        
        // Tap two untapped Merfolk you control: Untap Benthicore. It gains shroud until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), new TapTargetCost(new TargetControlledPermanent(2, filter)));
        ability.addEffect(new GainAbilitySourceEffect(ShroudAbility.getInstance(), Duration.EndOfTurn).setText("it gains shroud until end of turn"));
        this.addAbility(ability);
    }

    private Benthicore(final Benthicore card) {
        super(card);
    }

    @Override
    public Benthicore copy() {
        return new Benthicore(this);
    }
}