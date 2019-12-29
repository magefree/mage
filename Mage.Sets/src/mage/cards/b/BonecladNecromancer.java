package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BonecladNecromancer extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card from a graveyard");

    public BonecladNecromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Boneclad Necromancer enters the battlefield, you may exile target creature card from a graveyard. If you do, create a 2/2 black Zombie creature token.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect(), true);
        ability.addTarget(new TargetCardInGraveyard(filter));
        ability.addEffect(new CreateTokenEffect(new ZombieToken()).concatBy("If you do,"));
        this.addAbility(ability);
    }

    private BonecladNecromancer(final BonecladNecromancer card) {
        super(card);
    }

    @Override
    public BonecladNecromancer copy() {
        return new BonecladNecromancer(this);
    }
}
