package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.FaerieRogueToken;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class ShadowPuppeteers extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public ShadowPuppeteers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}");
        
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // When Shadow Puppeteers enters the battlefield, create two 1/1 black Faerie Rogue creature tokens with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FaerieRogueToken(), 2)));

        // Whenever a creature you control with flying attacks, you may have it become a red Dragon with base power and toughness 4/4 in addition to its other colors and types until end of turn.
        Effect effect = new BecomesCreatureTargetEffect(
                new CreatureToken(4, 4, "red Dragon with base power and toughness 4/4")
                    .withColor("R")
                    .withSubType(SubType.DRAGON),
                false, false,
                Duration.EndOfTurn, false,
                true, false,
                false
        );
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(
                Zone.BATTLEFIELD, effect, true, filter, true
        ));
    }

    private ShadowPuppeteers(final ShadowPuppeteers card) {
        super(card);
    }

    @Override
    public ShadowPuppeteers copy() {
        return new ShadowPuppeteers(this);
    }
}
