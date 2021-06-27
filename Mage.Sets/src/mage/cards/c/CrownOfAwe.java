package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author BlueElectivire
 */
public final class CrownOfAwe extends CardImpl {

	public CrownOfAwe(UUID ownerId, CardSetInfo setInfo){
		super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

		this.suptype.add(subtype.AURA);

		// Enchant creature
		TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

		// Enchanted Creature has protection from black and from red.
		ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(ProtectionAbility.from(ObjectColor.BLACK, ObjectColor.RED), 
			AttachmentType.AURA, Duration.WhileOnBattlefield));
        this.addAbility(ability);

		// Sacrifice Crown of Awe: Enchanted creature and other creatures that share a creature type with it gain protection from black and from red until end of turn.
		ability = new SimpleActivatedAbility(new CrownOfAweEffect(), new SacrificeSourceCost());
		this.addAbility(ability);
	}

	private CrownOfAwe(final CrownOfAwe card){
		super(card);
	}

	@Override
	public CrownOfAwe copy(){
		return new CrownOfAwe(this);
	}
}

public final class CrownOfAweEffect extends OneShotEffect {

	private static class CrownOfAwePredicate implements Predicate<Card> {
		private final Card card;

		private CrownOfAwePredicate(Card card){
			this.card = card;
		}

		@Override
		public boolean apply(Card input, Game game){
			return input.shareCreatureTypes(game, card);
		}
	}

	public CrownOfAweEffect() {
		super(Outcome.Benefit);
		this.staticText = "Sacrifice Crown of Awe: Enchanted creature and other creatures that share a creature type with it gain protection from black and from red until end of turn.";
	}

	public CrownOfAweEffect(final CrownOfAweEffect effect) {
		super(effect);
	}

	@Override
	public CrownOfAweEffect copy() {
		return new CrownOfAweEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability Source) {
		// Enchanted creature
		ContinuousEffect effect = new GainAbilityAttachedEffect(ProtectionAbility.from(Objectcolor.BLACK, Objectcolor.RED).getInstance(), AttachmentType.AURA, Duration.EndOfTurn);
		game.addEffect(effect, source);
		
		// and other creatures that share a creature type with it
		Permanent enchantedCreature = game.getPermanent(source.getSourcePermanentOrLKI(game).getAttachedTo());
		FilterCreaturePermanent filter = new FilterCreaturePermanent();
		filter.add(new CrownOfAwePredicate(enchantedCreature));
		filter.add(Predicates.not(new MageObjectReferencePredicate(new MageObjectReference(enchantedCreature, game))));
		game.addEffect(effect, source);

		// have protection from black and from red until end of turn.
		return true;
	}
}