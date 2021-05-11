
package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.ThopterColorlessToken;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class WhirlerRogue extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped artifacts you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public WhirlerRogue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Whirler Rogue enters the battlefield, create two 1/1 colorless Thopter artifact creature tokens with flying.
        Effect effect = new CreateTokenEffect(new ThopterColorlessToken(), 2);
        effect.setText("create two 1/1 colorless Thopter artifact creature tokens with flying");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect));

        // Tap two untapped artifacts you control: Target creature can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeBlockedTargetEffect(), new TapTargetCost(new TargetControlledPermanent(2, 2, filter, true)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private WhirlerRogue(final WhirlerRogue card) {
        super(card);
    }

    @Override
    public WhirlerRogue copy() {
        return new WhirlerRogue(this);
    }
}
