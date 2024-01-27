package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.DetectiveToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InsideSource extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DETECTIVE);

    public InsideSource(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Inside Source enters the battlefield, create a 2/2 white and blue Detective creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new DetectiveToken())));

        // {3}, {T}: Target Detective you control gets +2/+0 and gains vigilance until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new BoostTargetEffect(2, 0)
                .setText("target Detective you control gets +2/+0"), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance())
                .setText("and gains vigilance until end of turn"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private InsideSource(final InsideSource card) {
        super(card);
    }

    @Override
    public InsideSource copy() {
        return new InsideSource(this);
    }
}
