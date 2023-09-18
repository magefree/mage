
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.util.functions.AbilityCopyApplier;

/**
 *
 * @author LevelX2
 */
public final class ProgenitorMimic extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("no Token");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public ProgenitorMimic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Progenitor Mimic enter the battlefield as a copy of any creature on the battlefield
        // except it has "At the beginning of your upkeep, if this creature isn't a token,
        // create a token that's a copy of this creature."
        Effect effect = new CreateTokenCopySourceEffect();
        effect.setText("create a token that's a copy of this creature");

        AbilityCopyApplier applier = new AbilityCopyApplier(
                new ConditionalInterveningIfTriggeredAbility(
                        new BeginningOfUpkeepTriggeredAbility(effect, TargetController.YOU, false),
                        new SourceMatchesFilterCondition(filter),
                        "At the beginning of your upkeep, if this creature isn't a token, create a token that's a copy of this creature.")
        );
        effect = new CopyPermanentEffect(applier);
        effect.setText("as a copy of any creature on the battlefield except it has \"At the beginning of your upkeep, if this creature isn't a token, create a token that's a copy of this creature.\"");
        this.addAbility(new EntersBattlefieldAbility(effect, true));
    }

    private ProgenitorMimic(final ProgenitorMimic card) {
        super(card);
    }

    @Override
    public ProgenitorMimic copy() {
        return new ProgenitorMimic(this);
    }
}
