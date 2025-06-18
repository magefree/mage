package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.util.functions.AbilityCopyApplier;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ProgenitorMimic extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("this creature isn't a token");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    private static final Condition condition = new SourceMatchesFilterCondition(filter);

    public ProgenitorMimic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Progenitor Mimic enter the battlefield as a copy of any creature on the battlefield
        // except it has "At the beginning of your upkeep, if this creature isn't a token,
        // create a token that's a copy of this creature."
        this.addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(new AbilityCopyApplier(
                new BeginningOfUpkeepTriggeredAbility(
                        new CreateTokenCopySourceEffect().setText("create a token that's a copy of this creature")
                ).withInterveningIf(condition)
        )).setText("as a copy of any creature on the battlefield, except it has " +
                "\"At the beginning of your upkeep, if this creature isn't a token, " +
                "create a token that's a copy of this creature.\""), true
        ));
    }

    private ProgenitorMimic(final ProgenitorMimic card) {
        super(card);
    }

    @Override
    public ProgenitorMimic copy() {
        return new ProgenitorMimic(this);
    }
}
