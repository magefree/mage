package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.GreenCat2Token;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class EsikasChariot extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("token you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public EsikasChariot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Esika's Chariot enters the battlefield, create two 2/2 green Cat creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GreenCat2Token(), 2)));

        // Whenever Esika's Chariot attacks, create a token that's a copy of target token you control.
        Ability ability = new AttacksTriggeredAbility(new CreateTokenCopyTargetEffect(), false);
        ability.addTarget(new TargetControlledPermanent(filter));
        this.addAbility(ability);

        // Crew 4
        this.addAbility(new CrewAbility(4));

    }

    private EsikasChariot(final EsikasChariot card) {
        super(card);
    }

    @Override
    public EsikasChariot copy() {
        return new EsikasChariot(this);
    }
}
