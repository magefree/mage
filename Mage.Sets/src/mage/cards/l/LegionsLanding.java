package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.IxalanVampireToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LegionsLanding extends TransformingDoubleFacedCard {

    public LegionsLanding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "{W}",
                "Adanto, the First Fort",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // Legion's Landing
        // When Legion's Landing enters the battlefield, create a 1/1 white Vampire creature token with lifelink.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new IxalanVampireToken())));

        // When you attack with three or more creatures, transform Legion's Landing.
        this.getLeftHalfCard().addAbility(new AttacksWithCreaturesTriggeredAbility(new TransformSourceEffect(), 3)
                .setTriggerPhrase("When you attack with three or more creatures, "));

        // Adanto, the First Fort
        // {T}: Add {W}.
        this.getRightHalfCard().addAbility(new WhiteManaAbility());

        // {2}{W}, {T}: Create a 1/1 white Vampire creature token with lifelink.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new IxalanVampireToken()), new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new TapSourceCost());
        this.getRightHalfCard().addAbility(ability);
    }

    private LegionsLanding(final LegionsLanding card) {
        super(card);
    }

    @Override
    public LegionsLanding copy() {
        return new LegionsLanding(this);
    }
}
