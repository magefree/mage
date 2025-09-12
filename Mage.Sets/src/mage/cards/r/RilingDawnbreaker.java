package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.OmenCard;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.Soldier22Token;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Jmlundeen
 */
public final class RilingDawnbreaker extends OmenCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public RilingDawnbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{4}{W}", "Signaling Roar", "{1}{W}");
        
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // At the beginning of combat on your turn, another target creature you control gets +1/+0 until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BoostTargetEffect(1, 0));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Signaling Roar
        // Create a 2/2 white Soldier creature token.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new Soldier22Token()));
        this.finalizeOmen();
    }

    private RilingDawnbreaker(final RilingDawnbreaker card) {
        super(card);
    }

    @Override
    public RilingDawnbreaker copy() {
        return new RilingDawnbreaker(this);
    }
}
